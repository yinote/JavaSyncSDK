package yinote.ydep.local;

import yinote.ydep.remote.dto.ResourceMetadata;
import yinote.ydep.remote.dto.ResourceCreateDto;
import yinote.ydep.remote.dto.ResourceUpdateDto;

public interface LocalResourceStore extends LocalStore<ResourceMetadata, ResourceCreateDto, ResourceUpdateDto>{

}
