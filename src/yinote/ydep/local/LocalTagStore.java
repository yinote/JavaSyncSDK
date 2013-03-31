package yinote.ydep.local;

import yinote.ydep.remote.dto.Tag;
import yinote.ydep.remote.dto.TagCreateDto;
import yinote.ydep.remote.dto.TagUpdateDto;

public interface LocalTagStore extends LocalStore<Tag, TagCreateDto, TagUpdateDto>{

}
