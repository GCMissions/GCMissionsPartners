/*
 * Project Name: wrw-admin
 * File Name: KdCharityController.java
 * Class Name: KdCharityController
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
package com.hengtiansoft.business.wrkd.activity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.wrkd.activity.dto.KdActivityDetailRequestDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdCharityDetailResponseDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdCharityDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdCharitySearchDto;
import com.hengtiansoft.business.wrkd.activity.service.KdCharityService;
import com.hengtiansoft.business.wrkd.feature.service.CoolBagService;
import com.hengtiansoft.common.controller.AbstractController;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.enums.AppImageType;

/**
 * Class Name: KdCharityController
 * Description: 酷袋公益活动
 * @author zhongyidong
 *
 */
@Controller
@RequestMapping(value = "/coolbag/charity")
public class KdCharityController extends AbstractController{
    
    // 添加公益活动
    private static final String ADD = "add";
    // 查看公益活动
    private static final String VIEW = "view";
    // 编辑公益活动
    private static final String EDIT = "edit";
    
    @Autowired
    private CoolBagService coolBagService;
    @Autowired
    private KdCharityService kdCharityService;
    
    
    /**
     * Description: 列表页面
     *
     * @param oper
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list() {
        return "wrkd/charity/charity_list";
    }
    
    /**
     * Description: 根据操作引导页面
     *
     * @param oper
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(@RequestParam String oper, @RequestParam(required = false) Long charityId, Model model) {
        // 图片数量限制
        model.addAttribute("minPicNum", 3);
        model.addAttribute("maxPicNum", 5);
        // 获取标签列表
        model.addAttribute("tagList", coolBagService.findImageList(AppImageType.TAG));
        if (ADD.equals(oper)) {
            return "wrkd/charity/charity_add";
        }
        if ((EDIT.equals(oper) || VIEW.equals(oper)) && null != charityId) {
            KdCharityDto charityDto = kdCharityService.findCharity(charityId);
            if (null != charityDto) {
                model.addAttribute("featureDto", coolBagService.findFeature(charityDto.getFeatureId()));
            }
            model.addAttribute("charityDto", charityDto);
            model.addAttribute("listImages", charityDto.getListImages());
        }
        model.addAttribute("oper", oper);
        return "wrkd/charity/charity_" + oper;
    }
    
    /**
     * Description: 根据条件查询公益活动
     *
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public KdCharitySearchDto listCharity(@RequestBody KdCharitySearchDto searchDto) {
        kdCharityService.searchCharity(searchDto);
        return searchDto;
    }
    
    /**
     * Description: 保存公益活动
     *
     * @param charityDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Long> saveCharity(@RequestBody KdCharityDto charityDto) {
        return kdCharityService.saveCharity(charityDto);
    }
    
    /**
     * Description: 保存公益活动详情
     *
     * @param chairtyDto
     * @return
     */
    @RequestMapping(value = "/saveDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> saveDetail(@RequestBody KdCharityDto charityDto) {
        return kdCharityService.saveDetail(charityDto.getId(), charityDto.getDetailDesc());
    }
    
    /**
     * Description: 保存公益活动反馈详情
     *
     * @param chairtyDto
     * @return
     */
    @RequestMapping(value = "/saveFeedback", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Long> saveFeedback(@RequestBody KdCharityDto charityDto) {
        int row = kdCharityService.saveFeedback(charityDto.getId(), charityDto.getFeedback());
        if (0 == row) {
            return ResultDtoFactory.toNack("保存失败");
        }
        return ResultDtoFactory.toAck("保存成功");
    }
    
    /**
     * Description: 删除公益活动
     *
     * @param charityIds
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> deletedCharity(@RequestParam("charityIds[]") List<Long> charityIds) {
        return kdCharityService.deleteCharity(charityIds);
    }
    
    /**
     * Description: 获取富文本编辑器
     *
     * @param editorName
     * @param model
     * @return
     */
    @RequestMapping(value = "/getEditor", method = RequestMethod.GET)
    public String getEditor(@RequestParam("name") String editorName, Model model) {
        model.addAttribute("editorName", editorName);
        return "wrkd/charity/ueditor";
    }
    
    /**
     * 
    * Description: 预览
    *
    * @param id
    * @return
     */
    @RequestMapping(value = "/toPreview/{id}", method = RequestMethod.GET)
    public String toPreview(@PathVariable Long id, Model model) {
        model.addAttribute("charityId", id);
        return "wrkd/charity/charity_preview";
    }
    
    /**
     * 
    * Description: 公益详情
    *
    * @param requestDto
    * @return
     */
    @RequestMapping(value="/getCharityDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<KdCharityDetailResponseDto> getDetail(@RequestBody KdActivityDetailRequestDto requestDto){
        
        return kdCharityService.getDetail(requestDto);
    }
}
