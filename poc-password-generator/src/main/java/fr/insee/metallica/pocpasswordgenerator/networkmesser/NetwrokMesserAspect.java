package fr.insee.metallica.pocpasswordgenerator.networkmesser;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import fr.insee.metallica.pocpasswordgenerator.controller.PasswordController.UsernameDto;

@Aspect
@Component
public class NetwrokMesserAspect implements Ordered {

	@Around("@annotation(NetworkMesser) &&  @annotation(network)")
	public Object monitorThread(ProceedingJoinPoint joinPoint, NetworkMesser network) throws Throwable {
		if (network.value().equals("controller")) {
			var user = (UsernameDto) joinPoint.getArgs()[0];
			if (StringUtils.contains(user.getUsername(), "timeout")) {
				if (StringUtils.contains(user.getUsername(), "timeout-posttransaction")) {
					var res = joinPoint.proceed();
					Thread.sleep(10000);	
					return res;
				} else if (StringUtils.contains(user.getUsername(), "timeout-pretransaction")) {
					Thread.sleep(10000);
					return joinPoint.proceed();
				}
			}
		} else if (network.value().equals("repository")) {
			var user = (String) joinPoint.getArgs()[0];
			if (StringUtils.contains(user, "timeout-transaction")) {
				Thread.sleep(10000);
			}
			return joinPoint.proceed();
		}
		return joinPoint.proceed();
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
