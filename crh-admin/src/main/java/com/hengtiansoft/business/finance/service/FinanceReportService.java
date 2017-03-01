/*
 * Project Name: wrw-admin
 * File Name: FinanceReportService.java
 * Class Name: FinanceReportService
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.finance.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.finance.dto.ReportDetailSearchDto;
import com.hengtiansoft.business.finance.dto.ReportSearchDto;

/**
 * Class Name: FinanceReportService
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface FinanceReportService {

    void getZFinanceReport(ReportSearchDto dto);

    void getPFinanceReport(ReportSearchDto dto);

    HSSFWorkbook toExcle(ReportSearchDto dto);

    void getFinanceDetailReport(ReportDetailSearchDto dto);
}
