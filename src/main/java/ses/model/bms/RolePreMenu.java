package ses.model.bms;

/**
 * Description: 角色-菜单关联实体类
 *
 * @author Ye MaoLin
 * @version 2016-9-18
 * @since JDK1.7
 */
public class RolePreMenu {
	
	
	/** 角色 */
	private Role role;
	
	/** 菜单 */
	private PreMenu preMenu;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public PreMenu getPreMenu() {
		return preMenu;
	}

	public void setPreMenu(PreMenu preMenu) {
		this.preMenu = preMenu;
	}
	
}
