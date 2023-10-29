package Interface;

import java.util.List;

import Model.Chapter;
import Model.Comic;

public interface IChapterLoadDone {
    void onChapterLoadDoneList(List<Chapter> chapterList);
}
