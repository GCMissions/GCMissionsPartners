/*
 * Project Name: wrw-admin
 * File Name: ShopStaticController.java
 * Class Name: ShopStaticController
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.shopMall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.shopMall.service.ShopStaticService;

/**
* Class Name: ShopStaticController
* Description: 商城静态化接口
* @author zhisongliu
*
*/
@Controller
@RequestMapping(value = "/shopStatic")
public class ShopStaticController {
    
    
    @Autowired
    private ShopStaticService shopService;
    /**
     * 
    * Description: 首页
    *
    * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("shopStatic/shopStatic");

    }

}
