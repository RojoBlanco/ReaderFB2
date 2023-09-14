package FB2Format;

import java.util.ArrayList;

import FB2Format.DescriptionBlock.Description;
import FB2Format.BinaryBlock.Binary;
import FB2Format.BodyBlock.Body;

public class FictionBook {
    private Description desc = new Description();
    private ArrayList<Body> listBody = new ArrayList<Body>();
    private ArrayList<Binary> listBinary = new ArrayList<Binary>();

    public FictionBook(){}

    public FictionBook(Description desc, ArrayList<Body> listBody, ArrayList<Binary> listBinary){
        this.desc = desc;
        this.listBinary = listBinary;
        this.listBody = listBody;
    }
    public Description getDesc() {
        return desc;
    }

    public ArrayList<Binary> getListBinary() {
        return listBinary;
    }

    public ArrayList<Body> getListBody() {
        return listBody;
    }
}
