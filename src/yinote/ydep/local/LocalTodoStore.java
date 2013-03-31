package yinote.ydep.local;

import yinote.ydep.remote.dto.Todo;
import yinote.ydep.remote.dto.TodoCreateDto;
import yinote.ydep.remote.dto.TodoUpdateDto;

public interface LocalTodoStore extends LocalStore<Todo, TodoCreateDto, TodoUpdateDto>{

}
