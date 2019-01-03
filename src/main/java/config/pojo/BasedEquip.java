package config.pojo;

public class BasedEquip{
    protected String name;//装备名称
    protected String description;//准备描述
    protected int diamond;//所需砖石数量

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }
}
