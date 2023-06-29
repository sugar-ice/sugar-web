import subprocess

# 设置数据库连接信息
serverName = "localhost"
databaseName = "nojoke"
userName = "root"
password = "12345678"

# 设置导出SQL文件路径
sqlFilePath = "./docs/sql/web-sql.sql"

# 导出SQL数据
exportCommand = f"mysqldump --host={serverName} --user={userName} --password={password} --databases {databaseName} > {sqlFilePath}"
subprocess.run(exportCommand, shell=True)
