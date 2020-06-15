package role;

/**
 * 地主
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/15 11:01
 */
public class LandLord extends Role {
    @Override
    public Long getId() {
        return 2L;
    }

    @Override
    public String getRoleName() {
        return "地主";
    }
}
