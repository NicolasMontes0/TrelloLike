package com.trellolike.model.service.Implementation;

import com.trellolike.model.exception.ApiRequestException;
import com.trellolike.model.model.Tag;
import com.trellolike.model.repository.TagRepository;
import com.trellolike.model.service.Interface.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag get(Integer id) {
        if(tagRepository.existsById("" + id))
            return tagRepository.findById("" + id).get();
        else
            throw new ApiRequestException("This Tag does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public Tag add(Tag tag) {
        if(tag.getName() == null || tag.getName().equals(""))
            throw new ApiRequestException("'name' must not be null or blank.", HttpStatus.BAD_REQUEST);
        return tagRepository.save(tag);
    }

    @Override
    public Tag update(Integer id, Tag newTag) {
        Optional<Tag> opTag = tagRepository.findById("" + id);
        if(opTag.isEmpty())
            throw new ApiRequestException("There is no tag with this id.", HttpStatus.NOT_FOUND);
        Tag tag = opTag.get();
        if(newTag.getId_tag() != null)
            throw new ApiRequestException("'id_tag' cannot be changed.", HttpStatus.BAD_REQUEST);
        if(newTag.getName() != null && !newTag.getName().equals("")) {
            tag.setName(newTag.getName());
        }
        return tagRepository.save(tag);
    }

    @Override
    public void remove(Integer id) {
        if(tagRepository.existsById("" + id))
            tagRepository.deleteById("" + id);
        else
            throw new ApiRequestException("This Tag does not exist.", HttpStatus.NOT_FOUND);
    }
}
