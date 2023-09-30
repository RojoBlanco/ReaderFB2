package FB2Format.DescriptionBlock;

import java.util.ArrayList;
import java.util.List;

public class Description {
    private TitleInfo titleInfo;
    private SrcTitleInfo srcTitleInfo;
    private Document_Info documentInfo;
    private PublishInfo publishInfo;
    private List<CustomInfo> customInfoLists;

    public void setTitleInfo(TitleInfo titleInfo){
        this.titleInfo = titleInfo;
    }

}
