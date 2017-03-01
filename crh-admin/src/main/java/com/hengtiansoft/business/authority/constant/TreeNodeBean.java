/*
 * Project Name: zc-collector-web-platform
 * File Name: TreeNodeBean.java
 * Class Name: TreeNodeBean
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
package com.hengtiansoft.business.authority.constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: TreeNodeBean
 * Description: 节点树
 * 
 * @author zhisongliu
 */
public class TreeNodeBean implements Serializable {

    private static final long  serialVersionUID = -8276116020118991126L;

    private Long               id;

    private String             nodeName;

    private Long               level;

    private String             nodeDesc;

    private Long               pid;

    private List<TreeNodeBean> childrenList     = new ArrayList<TreeNodeBean>();

    private TreeNodeBean       parentBean;

    private boolean            isLeaf;

    /**
     * @return return the value of the var id
     */
    public Long getId() {
        return id;
    }

    /**
     * Description: Set id value
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return return the value of the var nodeName
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Description: Set nodeName value
     * 
     * @param nodeName
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * @return return the value of the var level
     */
    public Long getLevel() {
        return level;
    }

    /**
     * Description: Set level value
     * 
     * @param level
     */
    public void setLevel(Long level) {
        this.level = level;
    }

    /**
     * @return return the value of the var nodeDesc
     */
    public String getNodeDesc() {
        return nodeDesc;
    }

    /**
     * Description: Set nodeDesc value
     * 
     * @param nodeDesc
     */
    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    /**
     * @return return the value of the var pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * Description: Set pid value
     * 
     * @param pid
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @return return the value of the var childrenList
     */
    public List<TreeNodeBean> getChildrenList() {
        return childrenList;
    }

    /**
     * Description: Set childrenList value
     * 
     * @param childrenList
     */
    public void setChildrenList(List<TreeNodeBean> childrenList) {
        this.childrenList = childrenList;
    }

    /**
     * @return return the value of the var parentBean
     */
    public TreeNodeBean getParentBean() {
        return parentBean;
    }

    /**
     * Description: Set parentBean value
     * 
     * @param parentBean
     */
    public void setParentBean(TreeNodeBean parentBean) {
        this.parentBean = parentBean;
    }

    /**
     * @return return the value of the var isLeaf
     */
    public boolean isLeaf() {
        return isLeaf;
    }

    /**
     * Description: Set isLeaf value
     * 
     * @param isLeaf
     */
    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
}
