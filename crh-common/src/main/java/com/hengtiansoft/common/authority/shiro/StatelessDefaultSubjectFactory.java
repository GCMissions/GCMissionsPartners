package com.hengtiansoft.common.authority.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * Class Name: StatelessDefaultSubjectFactory Description: Subject Factory class。cant't use session
 * 
 * @author taochen
 *
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        // don't create session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
