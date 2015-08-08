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
package com.ylw.gorm.envers.services

import grails.transaction.Transactional
import org.hibernate.envers.AuditReader
import org.hibernate.envers.AuditReaderFactory
import org.hibernate.envers.query.AuditQueryCreator

/**
 * Created by Yingliang Du
 */
@Transactional
class EnversService {

    // Inject session factory
    def sessionFactory



    def serviceMethod() {
        "Hello World new Date -> " + new Date()
    }

    def findAllRevisions(def gdc) {
        AuditReader reader = AuditReaderFactory.get(sessionFactory.currentSession)
        AuditQueryCreator queryCreator = reader.createQuery()
        queryCreator.forRevisionsOfEntity(gdc, false, true).resultList
    }

    def findRevisions(def gdc, def id) {
        AuditReader reader = AuditReaderFactory.get(sessionFactory.currentSession)
        reader.getRevisions(gdc, id);
    }

    def isAudited(def gdc) {
        AuditReader reader = AuditReaderFactory.get(sessionFactory.currentSession)
        reader.isEntityClassAudited(gdc)
    }

    def findCurrentRevision(def gdc) {
        AuditReader reader = AuditReaderFactory.get(sessionFactory.currentSession)
        reader.getCurrentRevision(gdc, false)
    }

    def findRevisionNumberForDate(Date date) {
        AuditReader reader = AuditReaderFactory.get(sessionFactory.currentSession)
        reader.getRevisionNumberForDate(date)
    }
}
