package com.epam.esm.controller;

import com.epam.esm.representation.CertificateModel;
import com.epam.esm.representation.assembler.CertificateModelAssembler;
import com.epam.esm.representation.page.PageModel;
import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.SearchCertificateDto;
import com.epam.esm.service.service.CertificateService;
import com.epam.esm.service.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

/**
 * The class provides certificate operations
 */
@RestController
@RequestMapping(value = "/certificates")
@Validated
public class CertificateController {

    private final CertificateService certificateService;
    private final PageService pageService;
    private final CertificateModelAssembler certificateModelAssembler;

    @Autowired
    public CertificateController(CertificateService certificateServiceImpl,
                                 PageService pageService,
                                 CertificateModelAssembler certificateModelAssembler) {
        this.certificateService = certificateServiceImpl;
        this.pageService = pageService;
        this.certificateModelAssembler = certificateModelAssembler;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateModel getGiftCertificate(
            @PathVariable("id") @Min(value = 1, message = "{valid_id}") Long id) {
        return certificateModelAssembler.toModel(certificateService.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageModel<CertificateModel> getGiftCertificates(
            @RequestParam Map<String, String> searchParams,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {

        SearchCertificateDto searchCertificateDto = SearchCertificateDto.builder()
                .tagNames(searchParams.get("tags"))
                .certificateName(searchParams.get("certificateName"))
                .description(searchParams.get("certificateDescription"))
                .sortParams(searchParams.get("sortParams"))
                .build();
        PageDto pageDto = new PageDto(page, size);

        List<CertificateDto> certificates = certificateService.findAllByParams(searchCertificateDto, pageDto);
        return new PageModel<>(
                certificateModelAssembler.toCollectionModel(certificates),
                pageService.buildCertificatesPage(pageDto, searchCertificateDto));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateModel createGiftCertificate(@RequestBody CertificateDto certificateDto) {
        return certificateModelAssembler.toModel(certificateService.create(certificateDto));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateModel updateGiftCertificate(
            @PathVariable @Min(value = 1, message = "{valid_id}") Long id,
            @RequestBody CertificateDto certificateDto) {
        certificateDto.setId(id);
        return certificateModelAssembler.toModel(certificateService.update(certificateDto));
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateModel patchGiftCertificate(
            @PathVariable @Min(value = 1, message = "{valid_id}") Long id,
            @RequestBody CertificateDto certificateDto) {
        certificateDto.setId(id);
        return certificateModelAssembler.toModel(certificateService.patch(certificateDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteGiftCertificate(
            @PathVariable @Min(value = 1, message = "{valid_id}") Long id) {
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
