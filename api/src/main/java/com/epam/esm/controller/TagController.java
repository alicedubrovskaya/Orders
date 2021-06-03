package com.epam.esm.controller;

import com.epam.esm.representation.TagModel;
import com.epam.esm.representation.assembler.TagModelAssembler;
import com.epam.esm.representation.page.PageModel;
import com.epam.esm.service.dto.PageDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.PageService;
import com.epam.esm.service.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * The class provides tag operations
 */
@RestController
@RequestMapping(value = "/tags")
@Validated
public class TagController {

    private final TagService tagService;
    private final PageService pageService;
    private final TagModelAssembler tagModelAssembler;

    @Autowired
    public TagController(TagService tagServiceImpl, PageService pageService, TagModelAssembler tagModelAssembler) {
        this.tagService = tagServiceImpl;
        this.pageService = pageService;
        this.tagModelAssembler = tagModelAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagModel createTag(@RequestBody TagDto tagDto) {
        return tagModelAssembler.toModel(tagService.create(tagDto));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagModel getTag(
            @PathVariable("id") @Min(value = 1, message = "{valid_id}") Long id) {
        return tagModelAssembler.toModel(tagService.findById(id));
    }

    @GetMapping(value = "/mostPopular")
    @ResponseStatus(HttpStatus.OK)
    public TagModel getMostPopularTag() {
        return tagModelAssembler.toModel(tagService.findMostPopularOfRichestUser());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageModel<TagModel> getTags(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        PageDto pageDto = new PageDto(page, size);
        List<TagDto> tags = tagService.findAll(pageDto);
        return new PageModel<>(tagModelAssembler.toCollectionModel(tags), pageService.buildTagsPage(pageDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteTag(
            @PathVariable @Min(value = 1, message = "{valid_id}") Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
