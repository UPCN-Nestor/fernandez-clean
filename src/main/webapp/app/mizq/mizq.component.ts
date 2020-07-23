/* eslint-disable */

import { Component, OnInit } from '@angular/core';

import { MenuItem, ConfirmationService } from 'primeng/api';
import { AccountService } from '../core/auth/account.service';
import { Router } from '@angular/router';
//import { NavbarService } from 'app/layouts/navbar/navbar.service';
//import { Suministro, ISuministro } from 'app/shared/model/suministro.model';
//import { SuministroService } from 'app/entities/suministro/suministro.service';
import { FacturaService } from '../entities/factura/factura.service';

import { ViewChild, ElementRef } from '@angular/core';
import { DeviceDetectorService } from 'ngx-device-detector';

@Component({
  selector: 'jhi-menuizq',
  templateUrl: './mizq.component.html',
  styles: []
})
export class MenuizqComponent implements OnInit {
  items: MenuItem[] = [];
  itemsSumi: MenuItem[] = [];
  itemsAutolectura: MenuItem[] = [];
  itemsSinSumi: MenuItem[] = [];

  // suministros: ISuministro[] = [];
  // sumi_activo: Suministro;

  displayNombre = false;
  alias: String = '';

  idsumi = 0;

  isMobile = false;

  mostrarAutolectura = false;
  mostrarSiempreAutolectura = false;

  mostrarAtencion = false;

  idsuminologin = 0;

  @ViewChild('bottom') bottomRef: ElementRef = new ElementRef<any>(null);

  constructor(
    private accountService: AccountService,
    private router: Router,
    // private navbarService: NavbarService,
    // protected suministroService: SuministroService,
    private confirmationService: ConfirmationService,
    private facturaService: FacturaService,
    private deviceService: DeviceDetectorService
  ) {}

  ngOnInit() {
    this.isMobile = this.deviceService.isMobile();

    this.refreshSuministros();
    this.cargarMenu();

    //this.suministros = null;
    //this.sumi_activo = null;

    /*
    if (this.isAuthenticated())
      this.accountService.hasAAuthority('ROLE_CONSULTA_AUTOLECTURAS').then(x => {
        this.mostrarAutolectura = x;
        this.mostrarSiempreAutolectura = x;
      });
    this.accountService.hasAuthority('ROLE_ATENCION').then(x => {
      this.mostrarAtencion = x;
    });*/

    /*
    this.suministroService.actualizarSuministros.subscribe(s => {
      //alert('actualizarSuministros');
      this.sumi_activo = s;
      this.refreshSuministros();
    });*/

    /*
    this.facturaService.actualizarPantalla.subscribe(() => {
      //alert('actualizarPantalla');
      //this.refreshSuministros();

      if (this.sumi_activo != null) this.navbarService.cambioSuministro.emit(this.sumi_activo);
    });

    this.facturaService.actualizarNoLogin.subscribe(idsumi => {
      this.idsuminologin = idsumi;
    });*/
  }

  mostrarOpcionesUsuario() {
    return !this.mostrarAtencion;
  }

  cargarMenu() {
    this.itemsSinSumi = [
      {
        label: 'Alta de suministro',
        icon: 'pi pi-arrow-circle-up',
        command: () => {
          this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          this.router.navigate(['/alta-suministro/new']);
        }
      },
      {
        label: 'Cambio de titularidad',
        icon: 'pi pi-arrow-circle-right',
        command: () => {
          this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          this.router.navigate(['/alta-suministro/cambio']);
        }
      }
    ];

    this.itemsSumi = [
      /*
      {
        label: 'Solicitar turno',
        icon: 'pi pi-fw pi-calendar',
        command: () => { 
          this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          this.router.navigate(['/turno/new']);
        }
      },      */
      {
        label: 'Agregar suministro',
        icon: 'pi pi-plus pi-download',
        command: () => {
          this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          this.router.navigate(['/suministro/new']);
        }
      },
      {
        label: 'Cambiar nombre',
        icon: 'pi pi-pencil',
        command: () => this.cambiarNombre()
      },
      {
        label: 'Desvincular',
        icon: 'pi pi-fw pi-trash',
        command: () => this.desvincularSuministro()
      }
    ];

    this.items = [
      {
        label: 'Mis facturas',
        icon: 'pi pi-fw pi-download',
        command: () => {
          this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          this.router.navigate(['/factura']);
        }
      },
      {
        label: 'Mis consumos',
        icon: 'pi pi-fw pi-chart-bar',
        command: () => {
          this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          this.router.navigate(['/consumos']);
        }
      },
      {
        label: 'Factura digital',
        icon: 'pi pi-fw pi-envelope',
        command: () => {
          this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          //  this.router.navigate(['/suministro/' + this.sumi_activo.id + ' /edit']);
        }
      },
      {
        label: 'Débito automático',
        icon: 'pi pi-fw pi-dollar',
        command: () => {
          this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          this.router.navigate(['/debaut']);
        }
      }
    ];

    this.itemsAutolectura = [
      {
        label: 'Autolecturas',
        icon: 'pi pi-fw pi-sort-numeric-up',
        command: () => {
          this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
          this.router.navigate(['/medicion-rurales']);
        }
      }
    ];
  }

  adherir() {
    this.router.navigate(['/register-upc']);
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  desvincularSuministro() {
    /*
    this.confirmationService.confirm({
      message: '¿Está seguro de que desea desvincular este suministro de la cuenta?',
      accept: () => {
        
        this.suministroService.delete(this.sumi_activo.id).subscribe(x => {
          this.suministroService.actualizarSuministros.emit(null);
          this.refreshSuministros();
          //this.router.navigate(['']);
        });
        
      }
    });*/
  }

  scroll() {
    this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }

  cambiarNombre() {
    //this.alias = this.sumi_activo.alias;
    this.displayNombre = true;
  }

  aceptarCambiarNombre() {
    this.displayNombre = false;
    /*
    this.suministroService.cambiarNombre(this.sumi_activo.id, this.alias).subscribe(
      res => {
        this.sumi_activo = res.body;
        this.navbarService.cambioSuministro.emit(this.sumi_activo);
        this.refreshSuministros();
      },
      e => {
        alert(e);
      }
    );
    */
  }

  /*
  estadoDeuda() {
    this.suministroService.estadoDeuda(this.sumi_activo.socio, this.sumi_activo.suministro).subscribe(
      response => {
        let file = new Blob([response], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);

        var a = document.createElement('a');
        document.body.appendChild(a);
        //a.style = "display: none";
        a.href = fileURL;
        a.download = this.sumi_activo.socio + '-' + this.sumi_activo.suministro + '-estado-deuda.pdf';
        a.target = '_blank';
        a.click();
      },
      res => {
        alert('Error al intentar obtener estado de deuda');
      }
    );
  }*/

  /*
  activarSuministro(s: Suministro) {
    this.sumi_activo = s;

    if (this.sumi_activo) {
      this.suministroService
        .esAutolectura(this.sumi_activo.socio, this.sumi_activo.suministro)
        .toPromise()
        .then(x => {
          this.mostrarAutolectura = x.body || this.mostrarSiempreAutolectura;
        });
    }

    this.navbarService.cambioSuministro.emit(s);
  }
*/

  agregarSuministro() {
    this.router.navigate(['/suministro/new']);
  }

  iconoDeServicio(s: any) {
    if (s === 1) return 'lightbulb';
    if (s === 5) return 'wifi';
    if (s === 10) return 'medkit';

    return 'exclamation-square';
  }

  nombreDeServicio(s: any) {
    if (s === 1) return 'Energía';
    if (s === 5) return 'Internet';
    if (s === 10) return 'Servicios Sociales';

    return 'exclamation-square';
  }

  ofrecerRegistro() {
    return this.idsuminologin || this.router.url === '/turno/new';
  }

  back() {
    /*
    this.facturaService.actualizarNoLogin.emit(undefined);*/
    window.history.back();
  }

  registrar() {
    const id = this.idsuminologin;
    /*
    this.facturaService.actualizarNoLogin.emit(undefined);*/
    this.router.navigate(['/register/' + id]);
  }

  atenderTurnos() {
    this.router.navigate(['/turno']);
  }

  idSumi(s: any) {
    if (s == null) return '';
    return s.socio + ('000' + s.suministro).slice(-3);
  }

  refreshSuministros() {
    /*
    this.sumi_activo = null;

    if (this.isAuthenticated())
      this.suministroService.findPropios().subscribe((res: HttpResponse<ISuministro[]>) => {
        this.suministros = res.body;
        if (this.suministros.length > 0) {
          if (this.sumi_activo == null) this.activarSuministro(this.suministros[this.suministros.length - 1]);
          else {
            let to_act = this.suministros.find(x => x.id == this.sumi_activo.id);
            //alert('a' + to_act.id);
            this.activarSuministro(to_act);
          }
        } else {
          this.sumi_activo = null;
          this.navbarService.cambioSuministro.emit(null);
        }

        //alert(this.suministros.length);
        return;
      }, null);*/
  }
}
