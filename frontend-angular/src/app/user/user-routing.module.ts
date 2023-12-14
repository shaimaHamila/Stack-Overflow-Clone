import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UserGuard } from '../auth-guards/user-gard/user.guard';
import { PostQuestionComponent } from './components/post-question/post-question.component';

const routes: Routes = [
  { path: "dashboard", component: DashboardComponent, canActivate: [UserGuard] },
  { path: "question", component: PostQuestionComponent, canActivate: [UserGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
