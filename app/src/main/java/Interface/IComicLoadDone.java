package Interface;

import java.util.List;

import Model.Comic;

public interface IComicLoadDone {
    void onComicLoadDoneList(List<Comic> comicList);
}
