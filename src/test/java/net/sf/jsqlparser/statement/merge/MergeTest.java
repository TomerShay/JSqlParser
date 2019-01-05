/*
 * Copyright (C) 2015 JSQLParser.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package net.sf.jsqlparser.statement.merge;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import static net.sf.jsqlparser.test.TestUtils.*;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author toben
 */
public class MergeTest {

    @Test
    public void testOracleMergeIntoStatement() throws JSQLParserException {
        String sql = "MERGE INTO bonuses B\n"
                + "USING (\n"
                + "  SELECT employee_id, salary\n"
                + "  FROM employee\n"
                + "  WHERE dept_no =20) E\n"
                + "ON (B.employee_id = E.employee_id)\n"
                + "WHEN MATCHED THEN\n"
                + "  UPDATE SET B.bonus = E.salary * 0.1\n"
                + "WHEN NOT MATCHED THEN\n"
                + "  INSERT (B.employee_id, B.bonus)\n"
                + "  VALUES (E.employee_id, E.salary * 0.05)  ";

        Statement statement = CCJSqlParserUtil.parse(sql);

        System.out.println(statement.toString());

        assertSqlCanBeParsedAndDeparsed(sql, true);
    }

    @Test
    public void testMergeIssue232() throws JSQLParserException {
        String sql = "MERGE INTO xyz using dual "
                + "ON ( custom_id = ? ) "
                + "WHEN matched THEN "
                + "UPDATE SET abc = sysdate "
                + "WHEN NOT matched THEN "
                + "INSERT (custom_id) VALUES (?)";

        assertSqlCanBeParsedAndDeparsed(sql, true);
    }

    @Test
    public void testMergeIssue676() throws JSQLParserException {
        String sql = "merge INTO M_KC21 USING\n"
                + "(SELECT AAA, BBB FROM I_KC21 WHERE I_KC21.aaa = 'li_kun'\n"
                + ") TEMP ON (TEMP.AAA = M_KC21.AAA)\n"
                + "WHEN MATCHED THEN\n"
                + "UPDATE SET M_KC21.BBB = 6 WHERE enterprise_id IN (0, 1)\n"
                + "WHEN NOT MATCHED THEN\n"
                + "INSERT VALUES\n"
                + "(TEMP.AAA,TEMP.BBB\n"
                + ")";

        assertSqlCanBeParsedAndDeparsed(sql, true);
    }

    @Test
    public void testMergeUpdateInsertOrderIssue401() throws JSQLParserException {
        assertSqlCanBeParsedAndDeparsed("MERGE INTO a USING dual ON (col3 = ? AND col1 = ? AND col2 = ?) WHEN NOT MATCHED THEN INSERT (col1, col2, col3, col4) VALUES (?, ?, ?, ?) WHEN MATCHED THEN UPDATE SET col4 = col4 + ?");
    }

    @Test
    public void testMergeUpdateInsertOrderIssue401_2() throws JSQLParserException {
        assertSqlCanBeParsedAndDeparsed("MERGE INTO a USING dual ON (col3 = ? AND col1 = ? AND col2 = ?) WHEN MATCHED THEN UPDATE SET col4 = col4 + ? WHEN NOT MATCHED THEN INSERT (col1, col2, col3, col4) VALUES (?, ?, ?, ?)");
    }

    @Test
    public void testMergeUpdateInsertOrderIssue401_3() throws JSQLParserException {
        try {
            assertSqlCanBeParsedAndDeparsed("MERGE INTO a USING dual ON (col3 = ? AND col1 = ? AND col2 = ?) WHEN MATCHED THEN UPDATE SET col4 = col4 + ? WHEN NOT MATCHED THEN INSERT (col1, col2, col3, col4) VALUES (?, ?, ?, ?) WHEN MATCHED THEN UPDATE SET col4 = col4 + ?");
            fail("syntaxerror parsed");
        } catch (JSQLParserException ex) {
            //expected to fail
        }
    }
}
