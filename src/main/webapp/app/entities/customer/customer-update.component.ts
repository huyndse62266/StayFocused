import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';

@Component({
  selector: '-customer-update',
  templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
  private _customer: ICustomer;
  isSaving: boolean;

  roles: IRole[];
  customerDOB: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private customerService: CustomerService,
    private roleService: RoleService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.customer = customer;
    });
    this.roleService.query().subscribe(
      (res: HttpResponse<IRole[]>) => {
        this.roles = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.customer.customerDOB = moment(this.customerDOB, DATE_TIME_FORMAT);
    if (this.customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(this.customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(this.customer));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>) {
    result.subscribe((res: HttpResponse<ICustomer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackRoleById(index: number, item: IRole) {
    return item.id;
  }
  get customer() {
    return this._customer;
  }

  set customer(customer: ICustomer) {
    this._customer = customer;
    this.customerDOB = moment(customer.customerDOB).format(DATE_TIME_FORMAT);
  }
}
