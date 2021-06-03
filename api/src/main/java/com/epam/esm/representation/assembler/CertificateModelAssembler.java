package com.epam.esm.representation.assembler;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.representation.CertificateModel;
import com.epam.esm.representation.PriceModel;
import com.epam.esm.service.dto.CertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateModelAssembler extends RepresentationModelAssemblerSupport<CertificateDto, CertificateModel> {
    private final TagModelAssembler tagModelAssembler;

    @Autowired
    public CertificateModelAssembler(TagModelAssembler tagModelAssembler) {
        super(CertificateController.class, CertificateModel.class);
        this.tagModelAssembler = tagModelAssembler;
    }

    @Override
    public CertificateModel toModel(CertificateDto certificateDto) {
        CertificateModel certificateModel = instantiateModel(certificateDto);
        CertificateController controller = methodOn(CertificateController.class);

        certificateModel.add(
                linkTo(controller.createGiftCertificate(null)).withRel("create"),
                linkTo(controller.updateGiftCertificate(certificateDto.getId(), null)).withRel("update"),
                linkTo(controller.deleteGiftCertificate(certificateDto.getId())).withRel("delete")
        );
        certificateModel.setDateOfCreation(certificateDto.getDateOfCreation());
        certificateModel.setDescription(certificateDto.getDescription());
        certificateModel.setDuration(certificateDto.getDuration());
        certificateModel.setId(certificateDto.getId());
        certificateModel.setDateOfModification(certificateDto.getDateOfModification());
        certificateModel.setName(certificateDto.getName());
        certificateModel.setTags(new ArrayList<>(tagModelAssembler.toCollectionModel(certificateDto.getTags()).getContent()));
        certificateModel.setPrice(new PriceModel(certificateDto.getPrice().getCost(), certificateDto.getPrice().getCurrency()));
        return certificateModel;
    }

    @Override
    public CollectionModel<CertificateModel> toCollectionModel(Iterable<? extends CertificateDto> certificateDtos) {
        CollectionModel<CertificateModel> collectionModel = super.toCollectionModel(certificateDtos);
        collectionModel.add(linkTo(methodOn(CertificateController.class).createGiftCertificate(null))
                .withRel("create"));
        return collectionModel;
    }
}
