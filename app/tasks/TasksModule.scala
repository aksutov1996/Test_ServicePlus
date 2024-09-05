package tasks

import play.api.inject._
import play.api.inject.SimpleModule

class TasksModule extends SimpleModule(bind[ClearCacheArea].toSelf.eagerly())