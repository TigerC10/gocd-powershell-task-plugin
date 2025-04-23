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

import com.thoughtworks.go.plugin.api.task.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PowerShellTaskExecutor {

    public Result execute(TaskConfig taskConfig, Context context, JobConsoleLogger console) {
        try {
            return runCommand(context, taskConfig, console);
        } catch (Exception e) {
            String errorMessage = "PowerShell execution failed: " + e.getMessage();
            console.printLine(errorMessage);
            return new Result(false, errorMessage);
        }
    }

    private Result runCommand(Context taskContext, TaskConfig taskConfig, JobConsoleLogger console) throws IOException, InterruptedException {
        ProcessBuilder powershell = createPowerShellCommandWithOptions(taskContext, taskConfig);
        console.printLine("Launching command: " + powershell.command());
        powershell.environment().putAll(taskContext.getEnvironmentVariables());
        console.printEnvironment(powershell.environment());

        Process powershellProcess = powershell.start();

        if (taskConfig.getMode().equals("Command")) {
            OutputStream outputStream = powershellProcess.getOutputStream();
            String commandScript = taskConfig.getCommand();
            File workingDir = new File(taskContext.getWorkingDir());
            File script = new File(workingDir, commandScript);
            InputStream fis = new FileInputStream(script.getAbsolutePath());
            byte[] buffer = new byte[1024];
            int read;
            while((read = fis.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            fis.close();
            outputStream.close();
        }

        console.readErrorOf(powershellProcess.getErrorStream());
        console.readOutputOf(powershellProcess.getInputStream());

        int exitCode = powershellProcess.waitFor();
        powershellProcess.destroy();

        if (exitCode != 0) {
            return new Result(false, "PowerShell execution failed. Please check the output.");
        }

        return new Result(true, "PowerShell execution complete.");
    }

    ProcessBuilder createPowerShellCommandWithOptions(Context taskContext, TaskConfig taskConfig) {
        List<String> command = new ArrayList<String>();
        command.add(taskConfig.getExe());

        if (taskConfig.getNoProfile()) {
            command.add("-NoProfile");
        }

        if (taskConfig.getNoLogo()) {
            command.add("-NoLogo");
        }

        if (!taskConfig.getExecutionPolicy().equals("Default")) {
            command.add("-ExecutionPolicy");
            command.add(taskConfig.getExecutionPolicy());
        }

        if (taskConfig.getMode().equals("File")) {
            command.add("-File");
            command.add(taskConfig.getFile());
        } else if (taskConfig.getMode().equals("Command")) {
            command.add("-Command");
            command.add("-");
        }

        return new ProcessBuilder(command);
    }
}
