package com.github.inikolaev.kungfu

import org.junit.platform.engine.EngineDiscoveryRequest
import org.junit.platform.engine.ExecutionRequest
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.TestExecutionResult
import org.junit.platform.engine.UniqueId
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor
import org.junit.platform.engine.support.descriptor.ClassSource

class TestEngine: org.junit.platform.engine.TestEngine {
    override fun getId() = "kungfu"

    override fun execute(request: ExecutionRequest?) {
        println("Executing test: $request")
        request?.let {
            val testDescriptor = request.rootTestDescriptor
            it.engineExecutionListener?.also { listener ->
                listener.dynamicTestRegistered(testDescriptor)
                listener.executionStarted(testDescriptor)
                //listener.executionFinished(testDescriptor, TestExecutionResult.failed(RuntimeException("test")))
                listener.executionFinished(testDescriptor, TestExecutionResult.successful())
                //listener.reportingEntryPublished(testDescriptor, ReportEntry.from(mapOf()))
            }
        }
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun discover(discoveryRequest: EngineDiscoveryRequest?, uniqueId: UniqueId?): TestDescriptor {
        println("discoveryRequest: $discoveryRequest, uniqueId: $uniqueId")

        return object:AbstractTestDescriptor(uniqueId, "ExecutorTest", ClassSource.from("ExampleTest")) {
            override fun getType() = TestDescriptor.Type.TEST
        }
    }
}