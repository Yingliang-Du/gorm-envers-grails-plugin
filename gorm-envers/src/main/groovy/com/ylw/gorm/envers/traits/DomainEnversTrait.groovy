/*
 * This file is part of
 *
 * Copyright (C) 2014-2015 The YLW-Java-Validation-Framework Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ylw.gorm.envers.traits

import com.ylw.gorm.envers.services.EnversService
import groovy.transform.CompileStatic
import org.hibernate.envers.Audited
import org.springframework.core.annotation.AnnotationUtils

/**
 * Created by Yingliang Du
 */
@CompileStatic
trait DomainEnversTrait {
    EnversService enversService;
    // Add service field to transient so it will not be mapped to database
    static transients = ['enversService']

    String currentDate() {
        return enversService.serviceMethod()
    }

//    def audited() {
//        AnnotationUtils.getAnnotation(this.class, Audited) != null
//    }

    def findAllRevisions() {
        enversService.findAllRevisions(this.class)
    }

    def isAudited() {
        enversService.isAudited(this.class)
    }

    def findCurrentRevision() {
        enversService.findCurrentRevision(this.class)
    }

    def findRevisionNumberForDate() {
        enversService.findRevisionNumberForDate(new Date())
    }
}