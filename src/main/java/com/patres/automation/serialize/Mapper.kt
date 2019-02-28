package com.patres.automation.serialize

import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel

interface Mapper<ModelType, SerializedType> {

    fun modelToSerialize(model: ModelType): SerializedType

    fun serializedToModel(serializedModel: SerializedType, root: RootSchemaGroupModel, parent: SchemaGroupModel): ModelType

}