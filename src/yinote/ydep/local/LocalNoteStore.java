package yinote.ydep.local;

import yinote.ydep.remote.dto.NoteCreateDto;
import yinote.ydep.remote.dto.NoteMetadata;
import yinote.ydep.remote.dto.NoteUpdateDto;

public interface LocalNoteStore extends LocalStore<NoteMetadata, NoteCreateDto, NoteUpdateDto>{

}
