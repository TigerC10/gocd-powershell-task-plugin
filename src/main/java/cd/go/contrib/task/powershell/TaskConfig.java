/*
 * Copyright 2025 Volusion, LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cd.go.contrib.task.powershell;

import java.util.Map;

public class TaskConfig {
    private final String exe;
    private final String mode;
    private final String file;
    private final String command;
    private final boolean noProfile;
    private final boolean noLogo;
    private final String executionPolicy;

    public TaskConfig(Map config) {
        exe = getValue(config, TaskPlugin.POWERSHELL_EXE_PROPERTY);
        mode = getValue(config, TaskPlugin.MODE_PROPERTY);
        file = getValue(config, TaskPlugin.FILE_PROPERTY);
        command = getValue(config, TaskPlugin.COMMAND_PROPERTY);
        noProfile = getBooleanValue(config, TaskPlugin.NO_PROFILE_PROPERTY);
        noLogo = getBooleanValue(config, TaskPlugin.NO_LOGO_PROPERTY);
        executionPolicy = getValue(config, TaskPlugin.EXECUTION_POLICY_PROPERTY);
    }

    private String getValue(Map config, String property) {
        return (String) ((Map) config.get(property)).get("value");
    }

    private boolean getBooleanValue(Map config, String property) {
        return (boolean) ((Map) config.get(property)).get("value").equals("true");
    }

    public String getExe() {
        return exe;
    }

    public String getMode() {
        return mode;
    }

    public String getFile() {
        return file;
    }

    public String getCommand() {
        return command;
    }
    
    public boolean getNoProfile() {
        return noProfile;
    }

    public boolean getNoLogo() {
        return noLogo;
    }
    
    public String getExecutionPolicy() { return executionPolicy; }
}
