package com.epam.esm.service.service.converter;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.entity.Certificate;
import com.epam.esm.service.entity.Tag;

public class CertificatePatchDtoConverter implements DtoConverter<Certificate, CertificateDto> {
    private final CertificateDtoConverter certificateConverter;
    private final TagDtoConverter tagConverter;

    public CertificatePatchDtoConverter() {
        this.certificateConverter = new CertificateDtoConverter();
        this.tagConverter = new TagDtoConverter();
    }

    @Override
    public Certificate convertToEntity(CertificateDto certificateDto, Certificate existingCertificate) {
        Certificate certificateFromRequest = certificateConverter.convertToEntity(certificateDto, new Certificate());

        if (certificateFromRequest.getName() != null) {
            existingCertificate.setName(certificateFromRequest.getName());
        }
        if (certificateFromRequest.getPrice() != null) {
            existingCertificate.setPrice(certificateFromRequest.getPrice());
        }
        if (certificateFromRequest.getDescription() != null) {
            existingCertificate.setDescription(certificateFromRequest.getDescription());
        }
        if (certificateFromRequest.getDuration() != null) {
            existingCertificate.setDuration(certificateFromRequest.getDuration());
        }

        for (TagDto tagDto: certificateDto.getTags()){
            if (existingCertificate.getTags().stream().noneMatch(tag -> tag.getName().equals(tagDto.getName()))){
                existingCertificate.getTags().add(tagConverter.convertToEntity(tagDto, new Tag()));
            }
        }
        return existingCertificate;
    }

    @Override
    public CertificateDto convertToDto(Certificate certificate) {
        throw new UnsupportedOperationException("Convert to dto is not permitted");
    }
}
