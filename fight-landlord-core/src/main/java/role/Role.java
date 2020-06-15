package role;

/**
 * 角色
 *
 * @author RubyJing
 * @version 1.0
 * @date 2020/6/9 17:14
 */
public class Role {
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role() {
    }

    public Role(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
