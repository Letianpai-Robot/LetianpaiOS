package com.letianpai;

import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public final class ShellUtils {
    private static final String LINE_SEP = System.getProperty("line.separator");

    private ShellUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Utils.Task<CommandResult> execCmdAsync(String command, boolean isRooted, Utils.Callback<CommandResult> callback) {
        return execCmdAsync(new String[]{command}, isRooted, true, callback);
    }

    public static Utils.Task<CommandResult> execCmdAsync(List<String> commands, boolean isRooted, Utils.Callback<CommandResult> callback) {
        return execCmdAsync(commands == null ? null : (String[])commands.toArray(new String[0]), isRooted, true, callback);
    }

    public static Utils.Task<CommandResult> execCmdAsync(String[] commands, boolean isRooted, Utils.Callback<CommandResult> callback) {
        return execCmdAsync(commands, isRooted, true, callback);
    }

    public static Utils.Task<CommandResult> execCmdAsync(String command, boolean isRooted, boolean isNeedResultMsg, Utils.Callback<CommandResult> callback) {
        return execCmdAsync(new String[]{command}, isRooted, isNeedResultMsg, callback);
    }

    public static Utils.Task<CommandResult> execCmdAsync(List<String> commands, boolean isRooted, boolean isNeedResultMsg, Utils.Callback<CommandResult> callback) {
        return execCmdAsync(commands == null ? null : (String[])commands.toArray(new String[0]), isRooted, isNeedResultMsg, callback);
    }

    public static Utils.Task<CommandResult> execCmdAsync(final String[] commands, final boolean isRooted, final boolean isNeedResultMsg,  Utils.Callback<CommandResult> callback) {
        return Utils.doAsync(new Utils.Task<CommandResult>(callback) {
            public CommandResult doInBackground() {
                return ShellUtils.execCmd(commands, isRooted, isNeedResultMsg);
            }
        });
    }

    public static CommandResult execCmd(String command, boolean isRooted) {
        return execCmd(new String[]{command}, isRooted, true);
    }

    public static CommandResult execCmd(List<String> commands, boolean isRooted) {
        return execCmd(commands == null ? null : (String[])commands.toArray(new String[0]), isRooted, true);
    }

    public static CommandResult execCmd(String[] commands, boolean isRooted) {
        return execCmd(commands, isRooted, true);
    }

    public static CommandResult execCmd(String command, boolean isRooted, boolean isNeedResultMsg) {
        return execCmd(new String[]{command}, isRooted, isNeedResultMsg);
    }

    public static CommandResult execCmd(List<String> commands, boolean isRooted, boolean isNeedResultMsg) {
        return execCmd(commands == null ? null : (String[])commands.toArray(new String[0]), isRooted, isNeedResultMsg);
    }

    public static CommandResult execCmd(String[] commands, boolean isRooted, boolean isNeedResultMsg) {
        int result = -1;
        if (commands != null && commands.length != 0) {
            Process process = null;
            BufferedReader successResult = null;
            BufferedReader errorResult = null;
            StringBuilder successMsg = null;
            StringBuilder errorMsg = null;
            DataOutputStream os = null;

            try {
                ProcessBuilder builder = new ProcessBuilder("sh");
                builder.redirectErrorStream(true); // 合并标准错误和标准输出
                process = builder.start();

                // process = Runtime.getRuntime().exec(isRooted ? SystemUtils.getSuAlias() : "sh");
                os = new DataOutputStream(process.getOutputStream());
                String[] var10 = commands;
                int var11 = commands.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    String command = var10[var12];
                    if (command != null) {
                        os.write(command.getBytes());
                        os.writeBytes(LINE_SEP);
                        os.flush();
                    }
                }

                os.writeBytes("exit" + LINE_SEP);
                os.flush();
                result = process.waitFor();
                if (isNeedResultMsg) {
                    successMsg = new StringBuilder();
                    errorMsg = new StringBuilder();
                    successResult = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                    errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
                    String line;
                    if ((line = successResult.readLine()) != null) {
                        successMsg.append(line);

                        while((line = successResult.readLine()) != null) {
                            Log.d("<<<", "读取到的："+line);
                            successMsg.append(LINE_SEP).append(line);
                        }
                    }

                    if ((line = errorResult.readLine()) != null) {
                        errorMsg.append(line);

                        while((line = errorResult.readLine()) != null) {
                            errorMsg.append(LINE_SEP).append(line);
                        }
                    }
                }
            } catch (Exception var30) {
                var30.printStackTrace();
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException var29) {
                    var29.printStackTrace();
                }

                try {
                    if (successResult != null) {
                        successResult.close();
                    }
                } catch (IOException var28) {
                    var28.printStackTrace();
                }

                try {
                    if (errorResult != null) {
                        errorResult.close();
                    }
                } catch (IOException var27) {
                    var27.printStackTrace();
                }

                if (process != null) {
                    process.destroy();
                }

            }

            return new CommandResult(result, successMsg == null ? "" : successMsg.toString(), errorMsg == null ? "" : errorMsg.toString());
        } else {
            return new CommandResult(result, "", "");
        }
    }

    public static class CommandResult {
        public int result;
        public String successMsg;
        public String errorMsg;

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }

        public String toString() {
            return "result: " + this.result + "\nsuccessMsg: " + this.successMsg + "\nerrorMsg: " + this.errorMsg;
        }
    }
}

