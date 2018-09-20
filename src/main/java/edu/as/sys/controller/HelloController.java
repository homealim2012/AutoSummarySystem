/**
 * @date 2016年3月10日 下午6:30:56
 * @version V1.0
 */
package edu.as.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {
//public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//public static final SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy-MM-dd");
//private static Logger mylog = LoggerFactory.getLogger(HelloController.class.getName());

//@InitBinder
//protected void initBinder(WebDataBinder binder) {
//binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
//}

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome() {
        return "index";

    }

//    //自动填充参数
//    @ResponseBody
//    @RequestMapping(value = "/autoParam", method = RequestMethod.GET)
//    public Map<String, String> printWelcome2(String name, String pass) {
//        Map<String, String> qqqMap = new HashMap<String, String>();
//        qqqMap.put("hello", "index");
//        qqqMap.put("name", "name");
//        qqqMap.put("pass", "pass");
//        return qqqMap;
//
//    }
//
//    //自动装箱
//    @ResponseBody
//    @RequestMapping(value = "/autoClass", method = RequestMethod.GET)
//    public Comment autoClass(Comment sss) {
//        return sss;
//
//    }
//
//    //直接返回string
//    @RequestMapping(value = "/StringRetuen", method = RequestMethod.GET)
//    public void StringRetuen(String sss, PrintWriter pw) {
//        pw.write("hello," + sss);
////        return sss;
//
//    }
//
//    //重定向
//    @RequestMapping("/redirectss")
//    public String redirect() {
//        return "redirect:autoParam";
//    }
//
//    //文件上传
//    @ResponseBody
//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public Map<String, String> upload(HttpServletRequest req) throws Exception {
//        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
//        MultipartFile file = mreq.getFile("file");
//        String filename = file.getOriginalFilename();
//        String filepath = req.getSession()
//                .getServletContext().getRealPath("upload/" + sdf_d.format(new Date())) + "/";
//        File fd = new File(filepath);
//        fd.mkdirs();
//        FileOutputStream fos = new FileOutputStream(filepath +
//                Utils.generate_DateTime_Filename() + filename.substring(filename.lastIndexOf('.')));
//        fos.write(file.getBytes());
//        fos.flush();
//        fos.close();
//        Map<String, String> qqqMap = new HashMap<String, String>();
//        qqqMap.put("status", "success");
//        return qqqMap;
//
//    }
//
//    //路径作为参数
//    @ResponseBody
//    @RequestMapping(value = "/{name:.+}", method = RequestMethod.GET)
//    public Comment hello(@PathVariable("name") String name) {
//        mylog.debug("start hello");
//        ModelAndView model = new ModelAndView();
//        model.setViewName("hello");
//        model.addObject("msg", name);
//        Map<String, String> qqqMap = new HashMap<String, String>();
//        qqqMap.put("name", name);
////        return model;
//        Comment one = new Comment();
//        return one;
//    }
//
//    //指定参数
//    @ResponseBody
//    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
//    public Comment hello2(@RequestParam(value = "id") String name) {
//        mylog.debug("start hello");
//        ModelAndView model = new ModelAndView();
//        model.setViewName("hello");
//        model.addObject("msg", name);
//        Map<String, String> qqqMap = new HashMap<String, String>();
//        qqqMap.put("name", name);
////        return model;
//        Comment one = new Comment();
//        return one;
//    }
//
//
//    //表单验证
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String add(@Valid Comment c, BindingResult br) {
//        if (br.getErrorCount() > 0) {
//            return "Hello/add";
//        }
//        return "showUser";
//    }
//
//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    public String add(Map<String, Object> map) {
//        map.put("comment", new Comment());
//        return "Hello/add";
//    }
//
//    @ModelAttribute
//    public void testModelAttribute() {
//        mylog.debug("start testModelAttribute");
//    }
//
//    @ModelAttribute
//    public void testModelAttribute2() throws Exception {
//        mylog.debug("start testModelAttribute2");
////        throw new Exception("dfffffffffffff");
//    }
//
//    @ModelAttribute
//    public void testModelAttribute3() {
//        mylog.debug("start testModelAttribute3");
////        mylog.error("354356 ");
//    }
//
//    @ExceptionHandler  //如果在controller类上加上@ControllerAdvice。那么，这个加了@ExceptionHandler的方法就会成为
//    //全局异常处理函数，还有一种异常处理方法，见springmvc配置页面
//    public void ffExceptionHandler(Exception ss) {
//        mylog.error(ss.getMessage());
////        mylog.error("354356 ");
//    }

}
