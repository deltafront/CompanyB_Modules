package companyB.flyway;

import companyB.common.utils.UtilityBase;
import org.flywaydb.core.api.MigrationInfo;

/**
 * Utility class for interacting with various aspects of the Flyway system.
 * @author Charles Burrell (deltafront@gmail.com)
 * @version  2.0.0
 */
public class FlywayUtils extends UtilityBase
{
    /**
     * Converts the MigrationInfo array into a string.
     * @param migrationInfos Array of MigrationInfo.
     * @return String representation of MigrationInfo array.
     * @since 2.0.0
     */
    public String migrationInfoToString(MigrationInfo[]migrationInfos)
    {
        String migrationInfoString = "Migration Infos:";
        for(MigrationInfo migrationInfo : migrationInfos)
        {
            migrationInfoString += "\n\tMigration Info:";
            migrationInfoString += String.format("\n\t\tScript: %s", migrationInfo.getScript());
            migrationInfoString += String.format("\n\t\tVersion: %s", migrationInfo.getVersion().getVersion());
            migrationInfoString += String.format("\n\t\tDescription: %s", migrationInfo.getDescription());
            migrationInfoString += String.format("\n\t\tInstallation Date: %s", migrationInfo.getInstalledOn());
            migrationInfoString += String.format("\n\t\tState: %s", migrationInfo.getState().getDisplayName());
            migrationInfoString += String.format("\n\t\tType: %s", migrationInfo.getType());
        }
        LOGGER.trace(String.format("Returning migration info string:\n%s",migrationInfoString));
        return migrationInfoString;
    }
}
