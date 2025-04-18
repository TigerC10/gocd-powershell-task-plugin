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

import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.HashMap;

public class GetConfigRequest {

    public GoPluginApiResponse execute() {
        HashMap<String, Object> config = new HashMap<>();

        HashMap<String, Object> powershellVersion = new HashMap<>();
        powershellVersion.put("default-value", TaskPlugin.POWERSHELL_EXE_DEFAULT);
        powershellVersion.put("display-order", "0");
        powershellVersion.put("display-name", "PowerShell Version");
        powershellVersion.put("required", true);
        config.put(TaskPlugin.POWERSHELL_EXE_PROPERTY, powershellVersion);

        HashMap<String, Object> mode = new HashMap<>();
        mode.put("display-order", "1");
        mode.put("display-name", "Mode");
        mode.put("required", true);
        config.put(TaskPlugin.MODE_PROPERTY, mode);

        HashMap<String, Object> file = new HashMap<>();
        file.put("display-order", "2");
        file.put("display-name", "File");
        file.put("required", false);
        config.put(TaskPlugin.FILE_PROPERTY, file);

        HashMap<String, Object> command = new HashMap<>();
        command.put("display-order", "3");
        command.put("display-name", "Command");
        command.put("required", false);
        config.put(TaskPlugin.COMMAND_PROPERTY, command);

        HashMap<String, Object> noProfile = new HashMap<>();
        noProfile.put("display-order", "4");
        noProfile.put("display-name", "No Profile");
        noProfile.put("required", false);
        config.put(TaskPlugin.NO_PROFILE_PROPERTY, noProfile);

        HashMap<String, Object> noLogo = new HashMap<>();
        noLogo.put("display-order", "5");
        noLogo.put("display-name", "No Logo");
        noLogo.put("required", false);
        config.put(TaskPlugin.NO_LOGO_PROPERTY, noLogo);

        HashMap<String, Object> executionPolicy = new HashMap<>();
        executionPolicy.put("display-order", "6");
        executionPolicy.put("display-name", "Execution Policy");
        executionPolicy.put("required", false);
        config.put(TaskPlugin.EXECUTION_POLICY_PROPERTY, executionPolicy);

        return DefaultGoPluginApiResponse.success(TaskPlugin.GSON.toJson(config));
    }
}
