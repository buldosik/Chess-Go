package com.example.chessgo.frontend.privatepolicy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.R
/**
 * PrivatePolicy screen to display the privacy policy information.
 *
 * @param navController NavHostController for navigation.
 */
@Composable
fun PrivatePolicy(navController: NavHostController = rememberNavController()){

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        InterpretationAndDefinitionSection()
        CollectingDataSection()
    }
}
/**
 * Section for interpretation and definitions in the privacy policy.
 */
@Composable
fun InterpretationAndDefinitionSection(){
    ParagraphSection(text = stringResource(id = R.string.policy_interpretation_paragraph_title))
    SectionTitle(text = stringResource(id = R.string.policy_interpretation_title))
    SectionBodyNormal(text = stringResource(id = R.string.policy_interpretation_info))
    SectionTitle(text = stringResource(id = R.string.definitions_title))
    SectionBodyNormal(text = stringResource(id = R.string.definitions_entering_part))
    SectionBodyNormal(text = stringResource(id = R.string.definitions_main_part_info))
}
/**
 * Section for collecting data in the privacy policy.
 */
@Composable
fun CollectingDataSection(){
    TypesOfDataSubsection()
    InfoCollectionAndItsUsageSubsection()
    RetentionTransferDeletionSubsection()
    DisclosurePersonalData()
    SecurityAndPrivacySubsection()
}
/**
 * Subsection for types of data in the privacy policy.
 */
@Composable
fun TypesOfDataSubsection(){
    ParagraphSection(text = stringResource(id = R.string.collection_personal_data_paragraph_title))
    SectionTitle(text = stringResource(id = R.string.data_types_subsection_title))
    SectionTitle(text = stringResource(id = R.string.personal_data_section_title))
    SectionBodyNormal(text = stringResource(id = R.string.personal_data_section_info))
    SectionBodyTabbed(text = stringResource(id = R.string.personal_data_list_of_collected_data))
    SectionTitle(text = stringResource(id = R.string.usage_data_section_title))
    SectionBodyNormal(text = stringResource(id = R.string.usage_data_section_info))
}
/**
 * Subsection for information collection and its usage in the privacy policy.
 */
@Composable
fun InfoCollectionAndItsUsageSubsection(){
    SectionTitle(text = stringResource(id = R.string.info_collected_in_app_section_title))
    SectionBodyNormal(text = stringResource(id = R.string.info_collected_in_app_entering_part))
    SectionBodyTabbed(text = stringResource(id = R.string.info_collected_in_app_second_part))
    SectionBodyNormal(text = stringResource(id = R.string.info_collected_in_app_ending_part))
    SectionTitle(text = stringResource(id = R.string.use_of_personal_data_section_title))
    SectionBodyTabbed(text = stringResource(id = R.string.use_of_personal_data_section_info))
    SectionBodyNormal(text = stringResource(id = R.string.use_of_personal_share_section_entering))
    //bullet list start
    SectionBodyTabbed(text = stringResource(id = R.string.use_of_personal_share_section_first))
    SectionBodyTabbed(text = stringResource(id = R.string.use_of_personal_share_section_second))
    SectionBodyTabbed(text = stringResource(id = R.string.use_of_personal_share_section_third))
    SectionBodyTabbed(text = stringResource(id = R.string.use_of_personal_share_section_forth))
    SectionBodyTabbed(text = stringResource(id = R.string.use_of_personal_share_section_fifth))
    SectionBodyTabbed(text = stringResource(id = R.string.use_of_personal_share_section_sixth))
    //bullet list end
}
/**
 * Subsection for retention, transfer, and deletion of data in the privacy policy.
 */
@Composable
fun RetentionTransferDeletionSubsection(){
    SectionTitle(text = stringResource(id = R.string.retention_section_title))
    SectionBodyNormal(text = stringResource(id = R.string.retention_section_info))
    SectionTitle(text = stringResource(id = R.string.transfer_data_section_title))
    SectionBodyNormal(text = stringResource(id = R.string.transfer_data_section_info))
    SectionTitle(text = stringResource(id = R.string.delete_data_section_title))
    SectionBodyNormal(text = stringResource(id = R.string.delete_data_section_info))
}
/**
 * Section for disclosure of personal data in the privacy policy.
 */
@Composable
fun DisclosurePersonalData(){
    SectionTitle(text = stringResource(id = R.string.disclosure_title))
    SectionTitle(text = stringResource(id = R.string.disclosure_business_title))
    SectionBodyNormal(text = stringResource(id = R.string.disclosure_business_info))
    SectionTitle(text = stringResource(id = R.string.disclosure_law_enforcement_title))
    SectionBodyNormal(text = stringResource(id = R.string.disclosure_law_enforcement_info))
    SectionTitle(text = stringResource(id = R.string.other_legal_requirement_title))
    SectionBodyNormal(text = stringResource(id = R.string.other_legal_requirement_entering))
    SectionBodyTabbed(text = stringResource(id = R.string.other_legal_requirement_list))
}
/**
 * Subsection for security and privacy in the privacy policy.
 */
@Composable
fun SecurityAndPrivacySubsection(){
    SectionTitle(text = stringResource(id = R.string.data_security_title))
    SectionBodyNormal(text = stringResource(id = R.string.data_security_info))
    SectionTitle(text = stringResource(id = R.string.children_privacy_title))
    SectionBodyNormal(text = stringResource(id = R.string.children_privacy_info))
    SectionTitle(text = stringResource(id = R.string.connection_with_other_sites_title))
    SectionBodyNormal(text = stringResource(id = R.string.connection_with_other_sites_info))
    SectionTitle(text = stringResource(id = R.string.privacy_policy_changes_title))
    SectionBodyNormal(text = stringResource(id = R.string.privacy_policy_changes_info))
    SectionTitle(text = stringResource(id = R.string.contact_title))
    SectionBodyNormal(text = stringResource(id = R.string.contact_info_entering))
    SectionBodyNormal(text = stringResource(id = R.string.contact_info_ending))
}
/**
 * Generic composable for a paragraph section with a bold heading.
 *
 * @param text Heading text for the paragraph.
 */
@Composable
fun ParagraphSection(text: String){
    Text(
        text = text,
        modifier = Modifier.padding(top = 20.dp, start = 5.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
        fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
        fontWeight = FontWeight.Bold,
    )
}
/**
 * Generic composable for a section title with a bold heading.
 *
 * @param text Heading text for the section.
 */
@Composable
fun SectionTitle(text : String){
    Text(
        text = text,
        modifier = Modifier.padding(top = 15.dp, start = 10.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
        fontWeight = FontWeight.Bold,
    )
}
/**
 * Generic composable for a normal section body text.
 *
 * @param text Body text for the section.
 */
@Composable
fun SectionBodyNormal(text: String){
    Text(
        text = text,
        modifier = Modifier.padding(top = 15.dp, start = 10.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
    )
}
/**
 * Generic composable for a tabbed section body text.
 *
 * @param text Body text for the section with indentation.
 */
@Composable
fun SectionBodyTabbed(text:String){
    Text(
        text = text,
        modifier = Modifier.padding(top = 15.dp, start = 40.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
    )
}
@Preview
@Composable
private fun Preview() {
    PrivatePolicy()
}