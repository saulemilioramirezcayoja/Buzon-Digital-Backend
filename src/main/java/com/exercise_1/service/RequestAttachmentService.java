package com.exercise_1.service;

import com.exercise_1.dto.FileUploadDTO;
import com.exercise_1.model.RequestAttachment;
import com.exercise_1.repository.RequestAttachmentRepository;
import com.exercise_1.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestAttachmentService {

    private final RequestAttachmentRepository requestAttachmentRepository;

    @Autowired
    public RequestAttachmentService(RequestAttachmentRepository requestAttachmentRepository) {
        this.requestAttachmentRepository = requestAttachmentRepository;
    }

    @Autowired
    private RequestRepository requestRepository;

    public List<RequestAttachment> findAll() {
        return requestAttachmentRepository.findAll();
    }

    public Optional<RequestAttachment> findById(Long id) {
        return requestAttachmentRepository.findById(id);
    }

    public RequestAttachment save(RequestAttachment requestAttachment) {
        return requestAttachmentRepository.save(requestAttachment);
    }

    public void deleteById(Long id) {
        requestAttachmentRepository.deleteById(id);
    }

    public List<RequestAttachment> processMultipleFileUploads(List<FileUploadDTO> fileUploadDTOList) {
        return fileUploadDTOList.stream()
                .map(this::processSingleFileUpload)
                .collect(Collectors.toList());
    }

    private RequestAttachment processSingleFileUpload(FileUploadDTO fileUploadDTO) {
        byte[] decodedBytes = Base64.getDecoder().decode(fileUploadDTO.getBase64EncodedFile());
        Path filePath = buildFilePath(fileUploadDTO);
        saveFileToDisk(filePath, decodedBytes);
        return buildRequestAttachment(fileUploadDTO, filePath.toString());
    }

    private Path buildFilePath(FileUploadDTO fileUploadDTO) {
        String baseDirectory = "C:/document_storage";
        String year = String.valueOf(java.time.LocalDate.now().getYear());
        String month = String.format("%02d", java.time.LocalDate.now().getMonthValue());
        String signedOrUnsigned = fileUploadDTO.getAttachmentType().toLowerCase();
        String requestDirectory = "request_" + fileUploadDTO.getRequestId();

        Path storagePath = Paths.get(baseDirectory, signedOrUnsigned, year, month, requestDirectory);
        try {
            if (!Files.exists(storagePath)) {
                Files.createDirectories(storagePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al crear directorios para almacenar el archivo", e);
        }

        return storagePath.resolve(StringUtils.cleanPath(fileUploadDTO.getFileName()));
    }

    private void saveFileToDisk(Path filePath, byte[] decodedBytes) {
        try {
            Files.write(filePath, decodedBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo en disco", e);
        }
    }

    private RequestAttachment buildRequestAttachment(FileUploadDTO fileUploadDTO, String filePath) {
        RequestAttachment.AttachmentType attachmentType;
        try {
            attachmentType = RequestAttachment.AttachmentType.valueOf(fileUploadDTO.getAttachmentType().toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de adjunto no válido: " + fileUploadDTO.getAttachmentType(), e);
        }

        RequestAttachment requestAttachment = new RequestAttachment();
        requestAttachment.setRequest(requestRepository.findById(fileUploadDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada")));
        requestAttachment.setFileName(fileUploadDTO.getFileName());
        requestAttachment.setFilePath(filePath);
        requestAttachment.setFileType(StringUtils.getFilenameExtension(fileUploadDTO.getFileName()));
        requestAttachment.setAttachmentType(attachmentType);
        requestAttachment.setUploadedAt(java.time.LocalDateTime.now());
        requestAttachment.setFileSize(fileUploadDTO.getFileSize());

        return requestAttachmentRepository.save(requestAttachment);
    }
}