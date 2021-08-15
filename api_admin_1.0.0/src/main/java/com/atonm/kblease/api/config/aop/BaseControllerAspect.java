package com.atonm.kblease.api.config.aop;

import com.atonm.core.common.constant.Constant;
import com.atonm.core.dto.AuthorityErrorDTO;
import com.atonm.kblease.api.config.define.CoDefineManager;
import com.atonm.kblease.api.utils.DateUtil;
import com.atonm.core.api.ApiResponse;
import com.atonm.kblease.api.utils.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @author jang jea young
 * @since 2018-11-05
 */
@Aspect
@Component
public class BaseControllerAspect {
    @Around("bean(*RestController)" + "&& !within(org.jooq..*) ")
    public Object onAroundHandler(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Date requestedTime = new Date();

		String authorities = SecurityUtils.getCurrentUserInfo().getRoleHierarchy();
		ArrayList<Map<String, Object>> apiList = CoDefineManager.getValues(Constant.KBL_ADMIN_API_ROLE);
		boolean isMatchApiUri = false;

		for(Map<String, Object> api : apiList) {
			if(new AntPathMatcher().match(String.valueOf(api.get("apiUrl")), request.getRequestURI())) {
				if(authorities != null && authorities.contains(String.valueOf(api.get("authority")))) {
					isMatchApiUri = true;
					break;
				}else if(String.valueOf(api.get("authority")).equalsIgnoreCase(Constant.API_ROLE_PERMIT_ALL)) {
					isMatchApiUri = true;
					break;
				}
			}
		}

//		ResUserInfo currentUser = SecurityUtils.getCurrentUserInfo();
        //if(currentUser.getUserId() != null) {
        	/*for (Object arg : pjp.getArgs()) {
        		if (arg instanceof BaseSearchDTO) {
        			BaseSearchDTO searchDTO = (BaseSearchDTO) arg;

        			searchDTO.setCurrentUserId(currentUser.getUserId())
        			searchDTO.setCurrentUserNo(currentUser.getUserNo());

        			break;
        		}
        	}*/

		/*ApiSearchDTO search = new ApiSearchDTO();

		if(currentUser.getUserId() != null) {
			search.setCurrentUserNo(currentUser.getUserNo());
		}
		search.setPid(request.getParameter("pid"));
		search.setChnnlCode(request.getParameter("chnnlCode"));
		search.setMethodName(request.getMethod());

		Object obj;

		ApiService apiService = AppContextManager.getBean(ApiService.class);


		ArrayList<Map<String, Object>> apiList = CoDefineManager.getValues("api_list");
		boolean isMatchApiUri = false;
		for(Map<String, Object> api : apiList) {
			if(String.valueOf(api.get("API_URL")).equalsIgnoreCase(request.getRequestURI()) && String.valueOf(api.get("METHOD_NAME")).equalsIgnoreCase(request.getMethod())) {
				isMatchApiUri = true;
				break;
			}
		}

		if(isMatchApiUri) {
			Method method = ((MethodSignature) pjp.getSignature()).getMethod();

			if (Objects.equals(request.getMethod(), HttpMethod.GET.name())) {
				search.setMappingValues(method.getAnnotation(GetMapping.class).value());
			} else if (Objects.equals(request.getMethod(), HttpMethod.POST.name())) {
				search.setMappingValues(method.getAnnotation(PostMapping.class).value());
			} else if (Objects.equals(request.getMethod(), HttpMethod.PUT.name())) {
				search.setMappingValues(method.getAnnotation(PutMapping.class).value());
			} else if (Objects.equals(request.getMethod(), HttpMethod.DELETE.name())) {
				search.setMappingValues(method.getAnnotation(DeleteMapping.class).value());
			}

			Optional<ApiDTO> api = apiService.findApi(search);

			if (api.isPresent()) {
				obj = pjp.proceed();
			} else {
				obj = ResponseEntity.badRequest().body(ApiResponse.notExistsApiAuthority(AuthorityErrorDTO.builder()
						.uri(request.getRequestURI())
						.method(request.getMethod())
						.build()
				));
			}
		}else{
			obj = pjp.proceed();
		}*/
		Object obj;
		if(isMatchApiUri) {
			obj = pjp.proceed();

			try{
				ResponseEntity entity = (ResponseEntity)obj;
				ObjectMapper mapper = new ObjectMapper();
				Date responsedTime = new Date();
				//ResponseEntity responseEntity = mapper.readValue(obj.toString(), ResponseEntity.class);
				ApiResponse apiResponse = mapper.convertValue(entity.getBody(), ApiResponse.class);
				apiResponse.setRequestTime(DateUtil.getDate(requestedTime, "yyyy-MM-dd HH:mm:ss:SSS"));
				apiResponse.setResponseTime(DateUtil.getDate(responsedTime, "yyyy-MM-dd HH:mm:ss:SSS"));
				apiResponse.setDifftime(String.valueOf(responsedTime.getTime() - requestedTime.getTime()));
				obj = new ResponseEntity(apiResponse, ((ResponseEntity) obj).getStatusCode());
			}catch(Exception e) {
				e.printStackTrace();

				/*if(obj.getClass().getName().equalsIgnoreCase("org.springframework.web.servlet.ModelAndView")) {
					ResponseEntity entity =  ResponseEntity.ok(ApiResponse.ok(obj));
					ObjectMapper mapper = new ObjectMapper();
					Date responsedTime = new Date();
					//ResponseEntity responseEntity = mapper.readValue(obj.toString(), ResponseEntity.class);
					ApiResponse apiResponse = mapper.convertValue(entity.getBody(), ApiResponse.class);
					apiResponse.setRequestTime(DateUtil.getDate(requestedTime, "yyyy-MM-dd HH:mm:ss:SSS"));
					apiResponse.setResponseTime(DateUtil.getDate(responsedTime, "yyyy-MM-dd HH:mm:ss:SSS"));
					apiResponse.setDifftime(String.valueOf(responsedTime.getTime() - requestedTime.getTime()));
					obj = new ResponseEntity(apiResponse, HttpStatus.OK);
				}*/
			}
		}else{
			obj = ResponseEntity.badRequest().body(ApiResponse.notExistsApiAuthority(AuthorityErrorDTO.builder()
					.uri(request.getRequestURI())
					.method(request.getMethod())
					.build()
			));
		}

		return obj;
    }
}
