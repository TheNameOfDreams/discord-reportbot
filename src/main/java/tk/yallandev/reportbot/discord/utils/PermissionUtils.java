package tk.yallandev.reportbot.discord.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class PermissionUtils {
	
	public static boolean hasRole(Member member, String roleName, boolean caseSensitive) {
		boolean hasRole = false;
		
		for (Role role : member.getRoles())
			if (caseSensitive ? role.getName().equalsIgnoreCase(roleName) : role.getName().equals(roleName)) {
				hasRole = true;
				break;
			}
		
		return hasRole;
	}

}
