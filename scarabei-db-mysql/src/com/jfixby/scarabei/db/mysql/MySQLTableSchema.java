
package com.jfixby.scarabei.db.mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.db.TableSchema;
import com.jfixby.scarabei.api.log.L;

class MySQLTableSchema implements TableSchema {

	private final MySQLTable mySQLTable;
	private final Set<String> columns = Collections.newSet();

	public MySQLTableSchema (final MySQLTable mySQLTable) throws IOException {
		this.mySQLTable = mySQLTable;
		final MySQLConnection connection = this.mySQLTable.db.obtainConnection();
		connection.checkIsOpen();
		try {
			final Connection mysql_connection = connection.getConnection();
			final DatabaseMetaData meta = mysql_connection.getMetaData();
			final ResultSet resultSet = meta.getColumns(this.mySQLTable.getName(), null, this.mySQLTable.sql_table_name, "%");
			while (resultSet.next()) {
				this.columns.add(resultSet.getString(4));
// log.info("Column Name of table " + tableName + " = " + );
			}

		} catch (final SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		} finally {
			this.mySQLTable.db.releaseConnection(connection);
		}
	}

	@Override
	public Collection<String> getColumns () {
		return this.columns;
	}

	@Override
	public int indexOf (final String key) {
		final int result = this.columns.indexOf(key);
		if (result == -1) {
			L.e("schema", this.columns);
		}
		return result;
	}

}
