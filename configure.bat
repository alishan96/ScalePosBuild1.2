@echo off
REM    Scale POS By Nexus Leads
REM    Copyright (c) 2014-2015  Nexus Leads
REM   
REM	
REM
REM    This file is part of Wanda POS.
REM
REM    Wanda POS is free software: you can redistribute it and/or modify
REM    it under the terms of the GNU General Public License as published by
REM    the Free Software Foundation, either version 3 of the License, or
REM    (at your option) any later version.
REM
REM    Wanda POS is distributed in the hope that it will be useful,
REM    but WITHOUT ANY WARRANTY; without even the implied warranty of
REM    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
REM    GNU General Public License for more details.
REM
REM    You should have received a copy of the GNU General Public License
REM    along with Wanda POS.  If not, see <http://www.gnu.org/licenses/>.

set DIRNAME=%~dp0
set CP="%DIRNAME%wandapos.jar"
set CP=%CP%;"%DIRNAME%locales/"
set CP=%CP%;"%DIRNAME%lib/substance.jar"
start /B javaw -cp %CP% com.openbravo.pos.config.JFrmConfig