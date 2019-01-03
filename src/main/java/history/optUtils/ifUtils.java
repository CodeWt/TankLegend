package history.optUtils;

/**
 * @Created by  qiao
 * @date 18-4-17 下午8:15
 */

public class ifUtils implements TankCodeOptUtils{
    private String code;
    private CodeUtil codeUtil;
    private TankResponseUtil tankResponseUtil;

    public ifUtils(String code) {
        this.code = code;
        this.getCodeUtil(code);
    }

    @Override
    public TankResponseUtil opt(CodeUtil codeUtil) {


        this.tankResponseUtil=tankResponseUtil;
        return this.tankResponseUtil;
    }

    @Override
    public CodeUtil getCodeUtil(String code) {



        this.codeUtil=codeUtil;
        return this.codeUtil;
    }
}
