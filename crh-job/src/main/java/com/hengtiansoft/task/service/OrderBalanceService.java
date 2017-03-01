/*
 * Project Name: wrw-job
 * File Name: OrderBalanceService.java
 * Class Name: OrderBalanceService
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
package com.hengtiansoft.task.service;

import java.util.List;

import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderReturnEntity;

/**
 * Class Name: OrderBalanceService
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface OrderBalanceService {

    List<MOrderMainEntity> getOrder();

    void orderBalance(MOrderMainEntity entity);

    List<MOrderReturnEntity> getOrderReturn();

    void orderReturn(MOrderReturnEntity entity);

}
