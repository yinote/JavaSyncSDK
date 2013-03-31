package yinote.ydep.local;

import yinote.ydep.remote.dto.Notebook;
import yinote.ydep.remote.dto.NotebookCreateDto;
import yinote.ydep.remote.dto.NotebookUpdateDto;

public interface LocalNotebookStore extends LocalStore<Notebook, NotebookCreateDto, NotebookUpdateDto>{

}
