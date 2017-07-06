package com.hengtiansoft.common.authority.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * Class Name: StatelessDefaultSubjectFactory Description: Subject factory class. Do not use session
 * 
 * @author taochen
 *
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        // Do not create session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
