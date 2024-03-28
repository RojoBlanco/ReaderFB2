package ru.read.reader.fb2format.DescriptionBlock;

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
    public void setDocumentInfo(Document_Info documentInfo) {this.documentInfo = documentInfo;}
    public TitleInfo getTitleInfo(){return this.titleInfo;}
}
