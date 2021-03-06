/*
 * Copyright 2016 Bryan Kelly
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.btkelly.gnag

import com.btkelly.gnag.extensions.GnagPluginExtension
import com.btkelly.gnag.tasks.GnagCheck
import com.btkelly.gnag.tasks.GnagReportTask
import com.btkelly.gnag.utils.ProjectHelper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.JavaExec

/**
 * Created by bobbake4 on 4/1/16.
 */
class GnagPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        GnagPluginExtension gnagPluginExtension = GnagPluginExtension.loadExtension(project)

        project.repositories.jcenter() // Unlikely to be missing in real projects; here for sample projects only.

        project.configurations.create("gnagDetekt")
        project.dependencies.add("gnagDetekt", "io.gitlab.arturbosch.detekt:detekt-cli:1.0.0.RC7-3")

        project.afterEvaluate { evaluatedProject ->
            ProjectHelper projectHelper = new ProjectHelper(evaluatedProject)

            GnagCheck.addTask(projectHelper, gnagPluginExtension)
            GnagReportTask.addTask(projectHelper, gnagPluginExtension.github)
        }
    }
}
