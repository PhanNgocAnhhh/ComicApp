package Interface;

import java.util.List;

import Model.Comic;

public interface IComicLoadDone {
    void onComicLoadDoneListener(List<Comic> comicList);
}
