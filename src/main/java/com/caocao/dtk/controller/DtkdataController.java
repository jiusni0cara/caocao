package com.caocao.dtk.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.caocao.common.utils.JSONUtils;
import com.caocao.dtk.domain.DTKData;
import com.caocao.dtk.domain.DTKResult;
import com.caocao.dtk.domain.DTKRootBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caocao.dtk.domain.DtkdataDO;
import com.caocao.dtk.service.DtkdataService;
import com.caocao.common.utils.PageUtils;
import com.caocao.common.utils.Query;
import com.caocao.common.utils.R;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
//import com.google.gson.Gson;

/**
 * @author CC
 * @email msgbus@163.com
 * @date 2018-01-28 22:16:14
 */

@Controller
@RequestMapping("/dtk/dtkdata")
public class DtkdataController {
    @Autowired
    private HttpServletRequest rre;

    @Autowired
    private DtkdataService dtkdataService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping()
    @RequiresPermissions("dtk:dtkdata:dtkdata")
    String Dtkdata() {
        return "dtk/dtkdata/dtkdata";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("dtk:dtkdata:dtkdata")
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
//    @ApiImplicitParam(name = "params", value = "用户详细实体user", required = true, dataType = "string")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "a", value = "a", required = true, dataType = "string"),
//            @ApiImplicitParam(name = "b", value = "b", required = true, dataType = "string")
//    })
//    public PageUtils list(@ApiParam(required=true, name="", value="参数") @RequestParam Map<String, Object> params) {
//    @ApiImplicitParam(name = "status_ids", required = false, allowMultiple = true, paramType = "string", dataType = "array")
//    @ApiImplicitParam dataType = "map"
    @ApiImplicitParam(name = "params", required = false, paramType = "query", dataType = "Map")
    public PageUtils list(@ApiParam @RequestParam Map<String, Object> params) {
        String url = "";
        url = rre.getScheme() + "://" + rre.getServerName()
                + ":" + rre.getServerPort()
                + rre.getServletPath();
        if (rre.getQueryString() != null) {
            url += "?" + rre.getQueryString();
        }
        System.out.println("!!!!!!!!!!!!");
        System.out.println(url);

        //查询列表数据
        Query query = new Query(params);
        System.out.println(JSONUtils.beanToJson(params));
        List<DtkdataDO> dtkdataList = dtkdataService.list(query);
        int total = dtkdataService.count(query);
        PageUtils pageUtils = new PageUtils(dtkdataList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("dtk:dtkdata:add")
    String add() {
        return "dtk/dtkdata/add";
    }

    @GetMapping("/edit/{apiType}")
    @RequiresPermissions("dtk:dtkdata:edit")
    String edit(@PathVariable("apiType") String apiType, Model model) {
        DtkdataDO dtkdata = dtkdataService.get(apiType);
        model.addAttribute("dtkdata", dtkdata);
        return "dtk/dtkdata/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("dtk:dtkdata:add")
    public R save(DtkdataDO dtkdata) {
        if (dtkdataService.save(dtkdata) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("dtk:dtkdata:edit")
    public R update(DtkdataDO dtkdata) {
        dtkdataService.update(dtkdata);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("dtk:dtkdata:remove")
    public R remove(String apiType) {
        if (dtkdataService.remove(apiType) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("dtk:dtkdata:batchRemove")
    public R remove(@RequestParam("ids[]") String[] apiTypes) {
        dtkdataService.batchRemove(apiTypes);
        return R.ok();
    }

    @ResponseBody
    @GetMapping("/cc")
//    @RequiresPermissions("test:list")
    public String cc(@RequestParam Map<String, Object> params) {
        //查询列表数据
//		Map<String, String> map = new HashMap<>();
//		map.put("picurl", "https://msgbus.tunnel.qydev.com/img/jx1.jpg");
//		map.put("openId", "ss");

        DTKData dtkData = new DTKData();
        dtkData.setApi_type("Api_type");

        DTKResult dtkResult = new DTKResult();
        dtkResult.setTitle(" Title");
        dtkResult.setD_title("D_title");
        dtkResult.setCid(11);
        DTKResult dtkResult2 = new DTKResult();
        dtkResult2.setCid(111);
        dtkResult.setIntroduce("5\u6761\u76d2\u88c5\uff0c\u9762\u6599\u8212\u9002\uff0c\u900f\u6c14\u4e0d\u7d27\u7ef7" +
                "\uff0c\u53cc\u5c42\u8bbe\u8ba1\uff0c\u7cbe\u5de5\u7ec6\u4f5c\uff0c\u8d34\u8fd1\u751f\u6d3b\uff0c" +
                "\u6e29\u99a8\u3002");

        DTKRootBean dtkRootBean = new DTKRootBean();
        dtkRootBean.setData(dtkData);

        ArrayList<DTKResult> dtkResults = new ArrayList<DTKResult>();
        dtkResults.add(dtkResult);
        dtkResults.add(dtkResult2);

        dtkRootBean.setResult(dtkResults);
//		List<DtkdataDO> items = new ArrayList<DtkdataDO>();
//		items.add(dtkdata);
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(items);

//        return jsonArray.toString();
        System.out.println(dtkRootBean.getResult().get(0).getTitle());
//		Gson gson=new Gson();
//		String gsonStr=gson.toJson(dtkRootBean);
        return JSON.toJSONString(dtkRootBean);
//		return  gsonStr;
    }

    /**
     * dataoke
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/dtkList")
//    @RequiresPermissions("test:list")
    public String dtkList() {
//        DTKRootBean u = this.restTemplate.getForObject("http://127.0.0.1/dtk/dtkdata/cc", DTKRootBean.class);
        String u = this.restTemplate.getForObject("http://api.dataoke.com/index" + ".php?r=Port/index&type=total&appkey=1353d567fb&v=2&page=1", String.class);

        DTKRootBean dtkRootBean = JSON.parseObject(u, DTKRootBean.class);

        return dtkRootBean.getResult().get(0).getPic();
//		DTKRootBean u = this.restTemplate.getForObject("http://api.dataoke.com/index .php?r=Port/index&type=total&appkey=1353d567fb&v=2&page=1", DTKRootBean.class);
//        return JSON.toJSONString(u);

//		Gson gson=new Gson();
//		String gsonStr=gson.toJson(u);
//		return  gsonStr;
    }

    /**
     * dataoke代理
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/dtkProxyList")
//    @RequiresPermissions("test:list")
    public String dtkProxyList() {
//      DTKRootBean u = this.restTemplate.getForObject("http://127.0.0.1/dtk/dtkdata/cc", DTKRootBean.class);
        String u = this.restTemplate.getForObject("http://api.dataoke.com/index.php?r=Port/index&type=total&appkey=1353d567fb&v=2&page=1", String.class);
        DTKRootBean dtkRootBean = JSON.parseObject(u, DTKRootBean.class);
        List<DTKResult> dtkress = dtkRootBean.getResult();
        StringBuffer sb = new StringBuffer();
        for (DTKResult result : dtkress) {
            sb.append(result.getPic());
        }
        return sb.toString();
//		DTKRootBean u = this.restTemplate.getForObject("http://api.dataoke.com/index .php?r=Port/index&type=total&appkey=1353d567fb&v=2&page=1", DTKRootBean.class);
//        return JSON.toJSONString(u);

//		Gson gson=new Gson();
//		String gsonStr=gson.toJson(u);
//		return  gsonStr;
    }
}
