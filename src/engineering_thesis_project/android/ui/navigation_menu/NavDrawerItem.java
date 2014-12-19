package engineering_thesis_project.android.ui.navigation_menu;

public class NavDrawerItem {
	private int iconID;
	private String title;
	//stream should be invisible in bluetooth mode
	private boolean visible = true;

	public int getIconID() {
		return iconID;
	}


	public void setIconID(int iconID) {
		this.iconID = iconID;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public NavDrawerItem(int icon, String name){
		this.iconID = icon;
		this.title = name;
	}


	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
}
