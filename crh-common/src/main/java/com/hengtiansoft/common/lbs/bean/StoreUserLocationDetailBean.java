/*
 * Project Name: zc-collect-common
 * File Name: StoreUserLocationDetailBean.java
 * Class Name: StoreUserLocationDetailBean
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
package com.hengtiansoft.common.lbs.bean;

/**
 * Class Name: StoreUserLocationDetailBean
 * Description: TODO
 * @author ruotuanxiao
 *
 */
public class StoreUserLocationDetailBean extends LocationDetailBean{
	
	private String longitude;
	
	private String latitude;
	
	private Integer user_id;

	/**
	 * @return return the value of the var longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Description: Set longitude value
	 * @param longitude
	 *             
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return return the value of the var latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Description: Set latitude value
	 * @param latitude
	 *             
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return return the value of the var user_id
	 */
	public Integer getUser_id() {
		return user_id;
	}

	/**
	 * Description: Set user_id value
	 * @param user_id
	 *             
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

}
